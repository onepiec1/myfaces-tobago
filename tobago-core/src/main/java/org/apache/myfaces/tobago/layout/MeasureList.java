/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class MeasureList implements Iterable<Measure>, Serializable {

  private List<Measure> list = new ArrayList<>();

  public MeasureList() {
  }

  @Override
  public Iterator<Measure> iterator() {
    return list.iterator();
  }

  public Stream<Measure> stream() {
    return list.stream();
  }

  public static MeasureList parse(final String string) {
    final MeasureList measureList = new MeasureList();
    final StringTokenizer tokenizer = new StringTokenizer(string, "; ");

    while (tokenizer.hasMoreTokens()) {
      final Measure token = Measure.valueOf(tokenizer.nextToken().trim());
      measureList.list.add(token);
    }
    return measureList;
  }

  public int getSize() {
    return list.size();
  }

  public Measure get(final int i) {
    return list.get(i);
  }

  public void add(final Measure measure) {
    list.add(measure);
  }

  public String serialize() {
    final StringBuilder str = new StringBuilder();
    for (final Measure measure : list) {
      str.append(measure);
      str.append(" ");
    }
    return str.toString();
  }

  public String toString() {
    return serialize();
  }
}
