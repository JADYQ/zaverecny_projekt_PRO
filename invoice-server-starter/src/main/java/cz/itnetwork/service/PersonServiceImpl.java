/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private PersonRepository personRepository;

    /**
     * Creates person in database
     *
     * @param personDTO Person to create
     * @return Dto with added person
     */
    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);
        return personMapper.toDTO(entity);
    }

    /**
     * Set person as hidden
     *
     * @param personId Person to delete
     */
    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }
    /**
     * Retrieves all persons not marked as hidden.
     *
     * @return A list of PersonDTOs for all visible persons.
     */
    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(entity -> personMapper.toDTO(entity))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the details of a person by their ID.
     *
     * @param id The ID of the person to retrieve.
     * @return A PersonDTO containing the details of the requested person.
     */
    @Override
    public PersonDTO getPerson(long id) {
        PersonEntity personEntity = fetchPersonById(id);
        return personMapper.toDTO(personEntity); //
    }

    /**
     * Updates the details of an existing person and creates a new record for the updated information. The original entity is marked as hidden.
     *
     * @param id     The ID of the person to update.
     * @param source The new details for the person as a PersonDTO.
     * @return The updated person details as a PersonDTO.
     */
    @Override
    public PersonDTO updateDetail(long id, PersonDTO source) {
        source.setId(null);   /** We need to set this value to null because when we receive a  DTO with a value,
                                    it gets saved in the database under the old ID instead of a new one*/
        PersonEntity oldEntity = fetchPersonById(id);
        oldEntity.setHidden(true);
        personRepository.save(oldEntity);
        PersonEntity newEntity = personMapper.toEntity(source);
        personRepository.save(newEntity);
        return personMapper.toDTO(newEntity);
    }

    /**
     * Fetches outgoing invoices for a person by their identification number. Including updates entities marked as hidden.
     *
     * @param identificationNumber The identification number of the person.
     * @return A list of InvoiceDTO representing the person's incoming invoices. Returns an empty list if no invoices are found.
     */
    @Override
    public List<InvoiceDTO> getOutgoingInvoicesByPerson(String identificationNumber) { // TODO : odstranit závislosti na Invoice z person InvoiceService přesunout do invoice serivcce
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber); // fetch a list of persons
        List<InvoiceDTO> fetchedInvoices = persons.stream()     // Converts a list of persons to a stream, then extracts and transforms their sellers into InvoiceDTOs using flatMap.
                .flatMap(personEntity -> personEntity.getSellers().stream())
                .map(invoice -> invoiceMapper.toDTO(invoice))
                .collect(Collectors.toList());
        return fetchedInvoices;

    }

    /**
     * Fetches incoming invoices for a person by their identification number. Including updated entities marked as hidden.
     *
     * @param identificationNumber The identification number of the person.
     * @return A list of InvoiceDTO representing the person's incoming invoices. Returns an empty list if no invoices are found.
     */
    @Override
    public List<InvoiceDTO> getIncomingInvoicesByPerson(String identificationNumber) {
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber); // fetch a list of persons
        List<InvoiceDTO> fetchedInvoices = persons.stream()     // Converts a list of persons to a stream, then extracts and transforms their purchases into InvoiceDTOs using flatMap.
                .flatMap(personEntity -> personEntity.getPurchases().stream())
                .map(invoice -> invoiceMapper.toDTO(invoice))
                .collect(Collectors.toList());
        return fetchedInvoices;

    }

    /**
     * Retrieves a list of statistics for all persons. Each entry in the list represents
     * a summary of financial data and identification for a single person, including their name,
     * ID, and total revenue generated from sales.
     *
     * @return A list of PersonStatisticsDTO objects, each containing the name, ID,
     * and total revenue of a person.
     */
    @Override
    public List<PersonStatisticsDTO> getPersonStatistics() {
        List<PersonEntity> personEntityList = personRepository.findByHidden(false); //Fetch a list of people from the database
        List<PersonStatisticsDTO> result = personEntityList.stream() // Converts the list of PersonEntities to a stream and then to a list of PersonStatisticsDTOs with information about names, IDs, and total sales revenue.
                .map(personEntity -> {
                    PersonStatisticsDTO personStatisticsDTO = new PersonStatisticsDTO();
                    personStatisticsDTO.setPersonName(personEntity.getName());
                    personStatisticsDTO.setPersonId(personEntity.getId());
                    personStatisticsDTO.setRevenue(personEntity.getSellers().stream()
                            .mapToDouble(invoice -> invoice.getPrice()).sum());
                    return personStatisticsDTO;
                })
                .collect(Collectors.toList());
        return result;
    }

    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws NotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}
