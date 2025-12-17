package com.core.aiProject5core.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.core.aiProject5core.dto.ChargerDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class EvChargerService {
	@Value("${ev.api.serviceKey}")
	private String serviceKey;

	@Value("${ev.api.pageNo:1}")
	private int pageNo;

	@Value("${ev.api.numOfRows:10}")
	private int numOfRows;

	@Value("${ev.api.connectTimeout:5000}")
	private int connectTimeout;

	@Value("${ev.api.readTimeout:10000}")
	private int reatdTimeout;

	private RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
		f.setConnectTimeout(connectTimeout);
		f.setReadTimeout(reatdTimeout);
		return new RestTemplate(f);
	}

	public List<ChargerDto> fetchChargers() {
		try {
			String baseUrl = "https://apis.data.go.kr/B552584/EvCharger/getChargerInfo";  // 공공API
			StringBuilder url = new StringBuilder(baseUrl);
			url.append("?serviceKey=").append(URLEncoder.encode(serviceKey, StandardCharsets.UTF_8));
			url.append("&pageNo=").append(pageNo);
			url.append("&numOfRows=").append(numOfRows);
			url.append("&zcode=11");
			url.append("&dataType=XML");

			String resp = restTemplate().getForObject(url.toString(), String.class);
			System.out.println("API 응답 XML: " + resp);

			// null/empty 체크
			if (resp == null || resp.isBlank()) return List.of();

			XmlMapper xmlMapper = new XmlMapper();
			JsonNode root = xmlMapper.readTree(resp.getBytes(StandardCharsets.UTF_8));

			// 공공 API 구조에 따라 items 경로를 찾아 파싱합니다. (환경에 따라 경로가 달라질 수 있으니 필요시 수정)
			JsonNode itemsNode = null;
			if (root.has("response")) {
				JsonNode response = root.get("response");
				if (response.has("body") && response.get("body").has("item")) {
					itemsNode = response.get("body").get("items").get("item");
				}
			}

			if (itemsNode == null) {
				itemsNode = root.findValue("item");
			}

			List<ChargerDto> list = new ArrayList<>();
			if (itemsNode == null) return list;

			if (itemsNode.isArray() ) {
				for (JsonNode item : itemsNode) {
					list.add(nodeToDto(item));
				}
			}						
			return list;		
		} catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

	private ChargerDto nodeToDto(JsonNode item) {
		ChargerDto dto = new ChargerDto();
		if (item.has("statId")) dto.setStatId(item.get("statId").asText());
		if (item.has("statNm")) dto.setStatNm(item.get("statNm").asText());
		if (item.has("addr")) dto.setAddr(item.get("addr").asText());
		if (item.has("lat")) dto.setLat(item.get("lat").asText());
		if (item.has("lng")) dto.setLng(item.get("lng").asText());

		return dto;
	}
}
