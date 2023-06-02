package com.binar.challenge4.controller;

import com.binar.challenge4.model.Invoice;
import com.binar.challenge4.service.InvoiceService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/getAllInvoice")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Invoice>> getAllInvoice() {
        List<Invoice> allInvoice = invoiceService.getAllInvoice();
        return ResponseEntity.ok(allInvoice);
    }

    @GetMapping("/generateInvoice")
    @PreAuthorize("hasRole('USER')")
    public void getInvoiceReport(Long invoiceId) throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=\"invoice.pdf\"");
        JasperPrint jasperPrint = invoiceService.generateInvoice(invoiceId);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @PostMapping("/addBooking")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Invoice> addDataForBooking(@RequestParam(name = "film_code") Long filmCode, @RequestParam(name = "schedule_id") Long scheduleId, @RequestParam(name = "seat_id") Long seatId) throws Exception {
        try {
            Invoice addDataForBooking = invoiceService.addDataForBooking(filmCode, scheduleId, seatId);
            return new ResponseEntity<>(addDataForBooking, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
