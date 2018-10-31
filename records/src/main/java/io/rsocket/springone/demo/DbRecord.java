package io.rsocket.springone.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Stephane Maldini
 */
@Table("records")
public class DbRecord {

	@Id
	private Integer    id;
	private String     name;
	private String     supername;
	private String     description;
	private String     background;
	private String     thumbnail;
	private Integer    comicCount;
	private Integer    eventCount;
	private Integer    pageviewCount;
	private Integer    serieCount;
	private Integer    storyCount;
	private String     marvelUrl;
	private String     wikipediaUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSupername() {
		return supername;
	}

	public void setSupername(String supername) {
		this.supername = supername;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getComicCount() {
		return comicCount;
	}

	public void setComicCount(Integer comicCount) {
		this.comicCount = comicCount;
	}

	public Integer getEventCount() {
		return eventCount;
	}

	public void setEventCount(Integer eventCount) {
		this.eventCount = eventCount;
	}

	public Integer getPageviewCount() {
		return pageviewCount;
	}

	public void setPageviewCount(Integer pageviewCount) {
		this.pageviewCount = pageviewCount;
	}

	public Integer getSerieCount() {
		return serieCount;
	}

	public void setSerieCount(Integer serieCount) {
		this.serieCount = serieCount;
	}

	public Integer getStoryCount() {
		return storyCount;
	}

	public void setStoryCount(Integer storyCount) {
		this.storyCount = storyCount;
	}

	public String getMarvelUrl() {
		return marvelUrl;
	}

	public void setMarvelUrl(String marvelUrl) {
		this.marvelUrl = marvelUrl;
	}

	public String getWikipediaUrl() {
		return wikipediaUrl;
	}

	public void setWikipediaUrl(String wikipediaUrl) {
		this.wikipediaUrl = wikipediaUrl;
	}

	public final Record toRecord() {
		{
			Record.Builder builder = Record.newBuilder();
			builder.setId(id);
			if (name != null) {
				builder.setName(name);
			}
			if (supername != null) {
				builder.setSuperName(supername);
			}

			if (description != null) {
				builder.setDescription(description);
			}
			if (background != null) {
				builder.setBackground(background);
			}
			if (thumbnail != null) {
				builder.setThumbnail(thumbnail);
			}
			if (comicCount != null) {
				builder.setComicCount(comicCount);
			}
			if (eventCount != null) {
				builder.setEventCount(eventCount);
			}
			if (pageviewCount != null) {
				builder.setPageviewCount(pageviewCount);
			}
			if (serieCount != null) {
				builder.setSerieCount(serieCount);
			}
			if (storyCount != null) {
				builder.setStoryCount(storyCount);
			}
			if (marvelUrl != null) {
				builder.setMarvelUrl(marvelUrl);
			}
			if (wikipediaUrl != null) {
				builder.setWikipediaUrl(wikipediaUrl);
			}
			return builder.build();
		}
	}
}
