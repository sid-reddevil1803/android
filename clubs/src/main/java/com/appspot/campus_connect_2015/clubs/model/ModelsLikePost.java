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
 * Model definition for ModelsLikePost.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clubs. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ModelsLikePost extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("from_pid")
  private java.lang.String fromPid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String postId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFromPid() {
    return fromPid;
  }

  /**
   * @param fromPid fromPid or {@code null} for none
   */
  public ModelsLikePost setFromPid(java.lang.String fromPid) {
    this.fromPid = fromPid;
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
  public ModelsLikePost setPostId(java.lang.String postId) {
    this.postId = postId;
    return this;
  }

  @Override
  public ModelsLikePost set(String fieldName, Object value) {
    return (ModelsLikePost) super.set(fieldName, value);
  }

  @Override
  public ModelsLikePost clone() {
    return (ModelsLikePost) super.clone();
  }

}
