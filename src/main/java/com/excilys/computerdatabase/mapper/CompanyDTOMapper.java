package main.java.com.excilys.computerdatabase.mapper;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computerdatabase.dto.CompanyDTO;
import main.java.com.excilys.computerdatabase.model.Company;

public enum CompanyDTOMapper {
    INSTANCE;

    public List<CompanyDTO> createDTOList(List<Company> companyList) {
        List<CompanyDTO> dtoList = new ArrayList<CompanyDTO>();

        for (Company company : companyList) {
            if (company != null) {
                CompanyDTO dto = new CompanyDTO();
                dto.setId(company.getId());
                dto.setName(company.getName());
                dtoList.add(dto);
            }
        }

        return dtoList;
    }
}
