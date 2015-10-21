/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-10-21 at 18:00:15 UTC 
 * Modify at your own risk.
 */

package com.appspot.campus_connect_2015.clubs.model;

/**
 * Model definition for ModelsEventForm.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clubs. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ModelsEventForm extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String attendees;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("club_id")
  private java.lang.String clubId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String collegeId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String completed;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String date;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("end_date")
  private java.lang.String endDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("end_time")
  private java.lang.String endTime;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String eventId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("event_creator")
  private java.lang.String eventCreator;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String isAlumni;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("start_date")
  private java.lang.String startDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("start_time")
  private java.lang.String startTime;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tags;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String time;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String venue;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String views;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAttendees() {
    return attendees;
  }

  /**
   * @param attendees attendees or {@code null} for none
   */
  public ModelsEventForm setAttendees(java.lang.String attendees) {
    this.attendees = attendees;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClubId() {
    return clubId;
  }

  /**
   * @param clubId clubId or {@code null} for none
   */
  public ModelsEventForm setClubId(java.lang.String clubId) {
    this.clubId = clubId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCollegeId() {
    return collegeId;
  }

  /**
   * @param collegeId collegeId or {@code null} for none
   */
  public ModelsEventForm setCollegeId(java.lang.String collegeId) {
    this.collegeId = collegeId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCompleted() {
    return completed;
  }

  /**
   * @param completed completed or {@code null} for none
   */
  public ModelsEventForm setCompleted(java.lang.String completed) {
    this.completed = completed;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDate() {
    return date;
  }

  /**
   * @param date date or {@code null} for none
   */
  public ModelsEventForm setDate(java.lang.String date) {
    this.date = date;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public ModelsEventForm setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate endDate or {@code null} for none
   */
  public ModelsEventForm setEndDate(java.lang.String endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEndTime() {
    return endTime;
  }

  /**
   * @param endTime endTime or {@code null} for none
   */
  public ModelsEventForm setEndTime(java.lang.String endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEventId() {
    return eventId;
  }

  /**
   * @param eventId eventId or {@code null} for none
   */
  public ModelsEventForm setEventId(java.lang.String eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEventCreator() {
    return eventCreator;
  }

  /**
   * @param eventCreator eventCreator or {@code null} for none
   */
  public ModelsEventForm setEventCreator(java.lang.String eventCreator) {
    this.eventCreator = eventCreator;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIsAlumni() {
    return isAlumni;
  }

  /**
   * @param isAlumni isAlumni or {@code null} for none
   */
  public ModelsEventForm setIsAlumni(java.lang.String isAlumni) {
    this.isAlumni = isAlumni;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStartDate() {
    return startDate;
  }

  /**
   * @param startDate startDate or {@code null} for none
   */
  public ModelsEventForm setStartDate(java.lang.String startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStartTime() {
    return startTime;
  }

  /**
   * @param startTime startTime or {@code null} for none
   */
  public ModelsEventForm setStartTime(java.lang.String startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTags() {
    return tags;
  }

  /**
   * @param tags tags or {@code null} for none
   */
  public ModelsEventForm setTags(java.lang.String tags) {
    this.tags = tags;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTime() {
    return time;
  }

  /**
   * @param time time or {@code null} for none
   */
  public ModelsEventForm setTime(java.lang.String time) {
    this.time = time;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public ModelsEventForm setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getVenue() {
    return venue;
  }

  /**
   * @param venue venue or {@code null} for none
   */
  public ModelsEventForm setVenue(java.lang.String venue) {
    this.venue = venue;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getViews() {
    return views;
  }

  /**
   * @param views views or {@code null} for none
   */
  public ModelsEventForm setViews(java.lang.String views) {
    this.views = views;
    return this;
  }

  @Override
  public ModelsEventForm set(String fieldName, Object value) {
    return (ModelsEventForm) super.set(fieldName, value);
  }

  @Override
  public ModelsEventForm clone() {
    return (ModelsEventForm) super.clone();
  }

}
