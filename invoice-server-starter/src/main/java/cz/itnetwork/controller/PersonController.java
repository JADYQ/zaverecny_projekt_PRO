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
package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/persons") // Creates a new person in the database
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    @GetMapping("/persons") // Gets a list of all persons
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/persons/{personId}") /* Delete one person with specific id - the selected person is
                                 marked as hidden and will no longer be visible in UI*/
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    @GetMapping("/persons/{personId}") // Gets detail of one person with an specific ID
    public PersonDTO showDetail(@PathVariable Long personId) {
        return personService.getPerson(personId);
    }

    @PutMapping("/persons/{personId}") /* Update person. Creates a new one and the old one is marked as hidden.
                            The old one will no longer be visible in UI*/
    public PersonDTO updateDetail(@PathVariable Long personId, @RequestBody PersonDTO personDTO) {
        return personService.updateDetail(personId, personDTO);
    }

    @GetMapping("/identification/{identificationNumber}/sales")//Gets a list of outgoing invoices for one person using its ID
        //
    public List<InvoiceDTO> getOutgoingInvoicesByPerson(@PathVariable String identificationNumber) {
        return personService.getOutgoingInvoicesByPerson(identificationNumber);
    }

    @GetMapping("/identification/{identificationNumber}/purchases")//Gets a list of incoming invoices for one person using its ID
    public List<InvoiceDTO> getIncomingInvoicesByPerson(@PathVariable String identificationNumber) {
        return personService.getIncomingInvoicesByPerson(identificationNumber);
    }
    @GetMapping("/persons/statistics") // Gets person statistics
    public List<PersonStatisticsDTO> getPersonStatistics(){
        return personService.getPersonStatistics();
    }

}