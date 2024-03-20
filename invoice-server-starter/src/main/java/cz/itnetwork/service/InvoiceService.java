package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.filter.InvoiceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);
    List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter);
    InvoiceDTO getInvoiceDetail(Long Id);
    void deleteInvoice(Long Id);
    InvoiceDTO updateInvoice(Long Id, InvoiceDTO data);
    InvoiceStatisticsDTO getStatistics();
}

