package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Exhibition;

import java.util.List;

public interface MainService {

    public List<Exhibition> getExhibitionList();

    public Exhibition getExhibitionById(Long exhibitionNo);

    public List<Exhibition> getExhibitionsByName();

    public List<Exhibition> searchByFilter(String selectedFilter, String searchInput);

}
