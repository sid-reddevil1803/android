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
 * on 2015-11-06 at 17:05:36 UTC 
 * Modify at your own risk.
 */

package com.appspot.campus_connect_2015.clubs.model;

/**
 * Model definition for ModelsCommentsForm.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clubs. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ModelsCommentsForm extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("comment_body")
  private java.lang.String commentBody;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String pid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String postId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String timestamp;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCommentBody() {
    return commentBody;
  }

  /**
   * @param commentBody commentBody or {@code null} for none
   */
  public ModelsCommentsForm setCommentBody(java.lang.String commentBody) {
    this.commentBody = commentBody;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPid() {
    return pid;
  }

  /**
   * @param pid pid or {@code null} for none
   */
  public ModelsCommentsForm setPid(java.lang.String pid) {
    this.pid = pid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPostId() {
    return postId;
  }

  /**
   * @param postId postId or {@code null} for none
   */
  public ModelsCommentsForm setPostId(java.lang.String postId) {
    this.postId = postId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp timestamp or {@code null} for none
   */
  public ModelsCommentsForm setTimestamp(java.lang.String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @Override
  public ModelsCommentsForm set(String fieldName, Object value) {
    return (ModelsCommentsForm) super.set(fieldName, value);
  }

  @Override
  public ModelsCommentsForm clone() {
    return (ModelsCommentsForm) super.clone();
  }

}
