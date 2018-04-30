package main.java.com.excilys.computerdatabase.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

public enum ComputerDTOMapper {
    INSTANCE;

    public List<ComputerDTO> createDTOList(List<Computer> cpuList) {
        List<ComputerDTO> dtoList = new ArrayList<ComputerDTO>();

        for (Computer cpu : cpuList) {
            if (cpu != null) {
                ComputerDTO dto = new ComputerDTO();
                dto.setId(cpu.getId());
                dto.setName(cpu.getName());

                LocalDate introduced = cpu.getIntroduced();
                if (introduced == null) {
                    dto.setIntroduced("");
                } else {
                    dto.setIntroduced(introduced.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                
                LocalDate discontinued = cpu.getDiscontinued();
                if (discontinued == null) {
                    dto.setDiscontinued("");
                } else {
                    dto.setDiscontinued(discontinued.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }

                Company company = cpu.getCompany();
                if (company==null) {
                    dto.setCompanyName("");
                } else {
                    dto.setCompanyName(company.getName());
                }
                dtoList.add(dto);
            }
        }

        return dtoList;
    }
}
