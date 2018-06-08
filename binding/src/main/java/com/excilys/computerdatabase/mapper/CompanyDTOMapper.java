package com.excilys.computerdatabase.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.CompanyDTO;
import com.excilys.computerdatabase.model.Company;

public class CompanyDTOMapper {

    public static List<CompanyDTO> createDTOList(List<Company> companyList) {
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
