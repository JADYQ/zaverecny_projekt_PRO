package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name="Invoice")
@Getter
@Setter
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long invoiceNumber;         
    @ManyToOne
    private PersonEntity buyer;
    @ManyToOne
    private PersonEntity seller;
    @Column(nullable = false)
    private LocalDate issued;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false)
    private String product;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Long vat;
    private String note;



    }
