package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.dto.ExhibitionData;
import com.kosa.emerjeonsiBack.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public void saveExhibitions(List<Exhibition> exhibitions) {
        AdminMapper.saveAll(exhibitions);
    }

    public List<ExhibitionData> getExhibitionDataList() {
        return adminMapper.getExhibitionDataList();
    }

}
