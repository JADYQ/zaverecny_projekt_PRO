package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return newly created person
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
    void removePerson(long id);


    List<PersonDTO> getAll();
    PersonDTO getPerson(long id);

    PersonDTO updateDetail(long id, PersonDTO personDTO);

    List<InvoiceDTO> getOutgoingInvoicesByPerson(String identificationNumber);

    List<InvoiceDTO> getIncomingInvoicesByPerson(String identificationNumber);

    List<PersonStatisticsDTO>getPersonStatistics();
}
