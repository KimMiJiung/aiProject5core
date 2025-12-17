package com.core.aiProject5core.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.core.aiProject5core.dto.MyFacilityDTO;



@Mapper
public interface ISearchRadius {
	public int searchCoung(int distance, double latTxt, double lngTxt);
	
	public ArrayList<MyFacilityDTO> searchRadius(int distance, double latTxt, double lngTxt, int start, int end);
	
}