package umc.project.umark.domain.report.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.project.umark.domain.report.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}



