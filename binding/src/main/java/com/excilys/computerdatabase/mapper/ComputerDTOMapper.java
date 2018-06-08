package com.excilys.computerdatabase.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.CDBException;

public class ComputerDTOMapper {

    public static List<ComputerDTO> createDTOList(List<Computer> cpuList) {
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
                    dto.setIntroduced(introduced.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }

                LocalDate discontinued = cpu.getDiscontinued();
                if (discontinued == null) {
                    dto.setDiscontinued("");
                } else {
                    dto.setDiscontinued(discontinued.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }

                Company company = cpu.getCompany();
                if (company == null) {
                    dto.setCompanyName(null);
                    dto.setCompanyId(null);
                } else {
                    dto.setCompanyId(company.getId());
                    dto.setCompanyName(company.getName());
                }
                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    public static Computer fromDTO(ComputerDTO dto)
            throws NumberFormatException, IllegalArgumentException, CDBException {
        if (dto == null) {
            throw new CDBException();
        } else if (dto.getId() == null) {
            return ComputerMapper.createComputer(dto.getName(), dto.getIntroduced(), dto.getDiscontinued(),
                    dto.getCompanyId().toString());
        } else {
            return ComputerMapper.createComputer(dto.getId().toString(), dto.getName(), dto.getIntroduced(),
                    dto.getDiscontinued(), dto.getCompanyId().toString());
        }
    }

}
