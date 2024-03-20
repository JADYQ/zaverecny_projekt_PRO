package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonMapper personMapper;

    /**
     * Saves a new invoice to the database using the provided InvoiceDTO.
     *
     * This method converts the InvoiceDTO to an entity, persists it, and returns the persisted invoice as a DTO.
     *
     * @param source The data for the invoice to be saved.
     * @return The saved invoice, converted back to a DTO.
     */
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO source) {
        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(source);
        PersonEntity buyer = personRepository.findById(invoiceEntity.getBuyer().getId()).orElseThrow();
        PersonEntity seller = personRepository.findById(invoiceEntity.getSeller().getId()).orElseThrow();
        invoiceEntity.setBuyer(buyer);
        invoiceEntity.setSeller(seller);
        invoiceEntity = invoiceRepository.save(invoiceEntity);
        return invoiceMapper.toDTO(invoiceEntity);
    }

    /**
     * Retrieves a list of invoices based on specified filtering criteria.
     *
     * Applies the given filter to find and return invoices within a defined limit. The filtering is
     * performed according to the criteria defined in the InvoiceFilter object.
     *
     * @param invoiceFilter The filtering criteria to apply to the invoice search.
     * @return A list of InvoiceDTOs matching the filter criteria, limited by the filter's limit.
     */
    @Override
    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);
        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceEntity -> invoiceMapper.toDTO(invoiceEntity))
                .toList();
    }

    /**
     * Retrieves detailed information about a specific invoice by its ID.
     *
     * @param invoiceId The ID of the invoice to retrieve.
     * @return An InvoiceDTO containing the invoice's details.
     */
    @Override
    public InvoiceDTO getInvoiceDetail(Long invoiceId) {
        return invoiceMapper.toDTO(fetchInvoiceById(invoiceId));
    }

    /**
     * Deletes an invoice identified by its ID.
     *
     * @param Id The ID of the invoice to be deleted.
     */
    @Override
    public void deleteInvoice(Long Id) {
        invoiceRepository.delete(fetchInvoiceById(Id));
    }

    /**
     * Updates an invoice using its ID with new information provided in an InvoiceDTO. It involves setting new buyer and seller
     * based on their IDs, applying changes to the invoice entity, and saving it. The updated invoice is then returned as a DTO.
     *
     * @param Id   The unique identifier of the invoice to be updated.
     * @param data An InvoiceDTO containing the new invoice data, including buyer and seller information.
     * @return An updated InvoiceDTO reflecting the applied changes.
     */
    @Override
    public InvoiceDTO updateInvoice(Long Id, InvoiceDTO data) {
        InvoiceEntity oldEntity = fetchInvoiceById(Id);
        PersonEntity buyer = personRepository.getReferenceById(data.getBuyer().getId());
        PersonEntity seller = personRepository.getReferenceById(data.getSeller().getId());
        InvoiceEntity newEntity = invoiceMapper.updateEntity(data, oldEntity);
        newEntity.setBuyer(buyer);
        newEntity.setSeller(seller); //In the data, only IDs are provided - we must manually set the buyer and seller objects.
        invoiceRepository.save(newEntity);
        return invoiceMapper.toDTO(newEntity);
    }

    /**
     * Retrieves aggregated invoice statistics.
     * <p>
     * This method aggregates statistics including the total sum of invoices for the current year,
     * the count of all invoices, and the total sum of all invoices ever created. If the sum for
     * the current year or the all-time sum is not available (null), they are set to 0.0. Values are solved
     * with by an SQL query in the repository.
     *
     * @return An InvoiceStatisticsDTO object containing the aggregated invoice statistics.
     */
    @Override
    public InvoiceStatisticsDTO getStatistics() {
        InvoiceStatisticsDTO statistics = new InvoiceStatisticsDTO();

        statistics.setCurrentYearSum(invoiceRepository.getCurrentYearSum()); //Value is resolved by an SQL query in the repositroy
        if (invoiceRepository.getCurrentYearSum() == null) {
            statistics.setCurrentYearSum(0.0);
        }
        statistics.setInvoicesCount(invoiceRepository.getInvoicesCount());
        statistics.setAllTimeSum(invoiceRepository.getAllTimeSum());
        if (invoiceRepository.getAllTimeSum() == null) {
            statistics.setAllTimeSum(0.0);
        }
        return statistics;
    }

    /**
     * Fetches an invoice by its ID.
     *
     * @param id The unique identifier of the invoice.
     * @return The found InvoiceEntity.
     * @throws NotFoundException If no invoice is found.
     */
    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
    }
}
