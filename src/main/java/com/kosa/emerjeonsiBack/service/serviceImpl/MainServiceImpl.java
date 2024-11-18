package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl {

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

    public List<Exhibition> searchByFilter(String selectedFilter, String searchInput) {
        return mainMapper.searchByFilter(selectedFilter, searchInput);
    }

}
