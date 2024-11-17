package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    private MainMapper mainMapper;

    public List<Exhibition> getExhibitionList() {

        return mainMapper.getAllExhibitions();
    }

    public Exhibition getExhibitionById(Long exhibitionNo) {

        return mainMapper.getExhibitionById(exhibitionNo);
    }

    public List<Exhibition> getExhibitionsByName() {
        return mainMapper.getExhibitionsByName();
    }

    public Object searchByFilter(String selectedFilter, String searchInput) {
        return mainMapper.searchByFilter(selectedFilter, searchInput);
    }
}
