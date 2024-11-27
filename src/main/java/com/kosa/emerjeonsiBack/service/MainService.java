package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MainService {

    public List<Exhibition> getExhibitionList();

    public Exhibition getExhibitionById(Long exhibitionNo);

    public List<Exhibition> getExhibitionsByName();

    public List<Exhibition> searchByFilter(String selectedFilter, String searchInput);

/*    public List<Exhibition> getExhibitionsByName() {
        return mainMapper.getExhibitionsByName();
    }

    public List<Exhibition> searchByFilter(String selectedFilter, String searchInput) {
        return mainMapper.searchByFilter(selectedFilter, searchInput);
    }*/

}
