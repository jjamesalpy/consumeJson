package com.joapps.astronautData.model;

/**
 * 
 * @author JOSEPH JAMES
 * This is a plain pojo class which acts as 
 * the astronaut model 
 *
 */
public class AstonautModel {
	public Long id;
	public String name;
	public String nationality;
	public String profileImageThumbnail;
	public String bio ;
	public String dateOfBirth;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getProfileImageThumbnail() {
		return profileImageThumbnail;
	}
	public void setProfileImageThumbnail(String profileImageThumbnail) {
		this.profileImageThumbnail = profileImageThumbnail;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
