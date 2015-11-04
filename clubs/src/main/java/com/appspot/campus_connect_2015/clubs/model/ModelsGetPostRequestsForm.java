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
 * on 2015-11-04 at 07:25:36 UTC 
 * Modify at your own risk.
 */

package com.appspot.campus_connect_2015.clubs.model;

/**
 * Model definition for ModelsGetPostRequestsForm.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clubs. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ModelsGetPostRequestsForm extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("club_name")
  private java.lang.String clubName;

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
  @com.google.api.client.util.Key("person_from")
  private java.lang.String personFrom;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String postRequestId;

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
   * @return value or {@code null} for none
   */
  public java.lang.String getClubName() {
    return clubName;
  }

  /**
   * @param clubName clubName or {@code null} for none
   */
  public ModelsGetPostRequestsForm setClubName(java.lang.String clubName) {
    this.clubName = clubName;
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
  public ModelsGetPostRequestsForm setDate(java.lang.String date) {
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
  public ModelsGetPostRequestsForm setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPersonFrom() {
    return personFrom;
  }

  /**
   * @param personFrom personFrom or {@code null} for none
   */
  public ModelsGetPostRequestsForm setPersonFrom(java.lang.String personFrom) {
    this.personFrom = personFrom;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPostRequestId() {
    return postRequestId;
  }

  /**
   * @param postRequestId postRequestId or {@code null} for none
   */
  public ModelsGetPostRequestsForm setPostRequestId(java.lang.String postRequestId) {
    this.postRequestId = postRequestId;
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
  public ModelsGetPostRequestsForm setTime(java.lang.String time) {
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
  public ModelsGetPostRequestsForm setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  @Override
  public ModelsGetPostRequestsForm set(String fieldName, Object value) {
    return (ModelsGetPostRequestsForm) super.set(fieldName, value);
  }

  @Override
  public ModelsGetPostRequestsForm clone() {
    return (ModelsGetPostRequestsForm) super.clone();
  }

}
