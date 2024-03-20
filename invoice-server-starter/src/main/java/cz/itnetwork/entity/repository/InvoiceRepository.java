package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaSpecificationExecutor<InvoiceEntity>,JpaRepository<InvoiceEntity, Long> {
        @Query(value = "SELECT SUM(price) FROM invoice WHERE YEAR(due_date) = YEAR(CURRENT_DATE());",nativeQuery = true)
        Double getCurrentYearSum(); // TODO: coalesce (SQL funkce)
        @Query(value = "SELECT SUM(price) FROM invoice;",nativeQuery = true)
        Double getAllTimeSum();
        @Query(value = "SELECT COUNT(*) FROM invoice;",nativeQuery = true)
        Long getInvoicesCount();
}
