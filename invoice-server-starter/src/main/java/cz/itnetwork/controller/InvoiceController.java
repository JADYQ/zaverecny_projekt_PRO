package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices") // Adds an invoice to the database
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO data) {
        return invoiceService.addInvoice(data);
    }

    @GetMapping("/invoices")  // Gets a list of invoices
    public List<InvoiceDTO> showInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getInvoices(invoiceFilter);
    }

    @GetMapping("/invoices/{invoiceId}") // Gets a detail of one invoice with a specific ID
    public InvoiceDTO showInvoiceDetail(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceDetail(invoiceId);
    }

    @DeleteMapping("/invoices/{invoiceId}") // Deletes one invoice with a specific ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }

    @PutMapping("/invoices/{invoiceId}") // Updates one invoice with specific ID
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO data) {
        return invoiceService.updateInvoice(invoiceId, data);
    }

    @GetMapping("invoices/statistics") // Gets invoice statistics
    public InvoiceStatisticsDTO showInvoicesStatistics() {
        return invoiceService.getStatistics();
    }
}


