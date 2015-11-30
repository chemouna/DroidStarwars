/*
 * Copyright (C) 2015 Fernando Franco Giráldez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mounacheikhna.ctc.lib.api.comicvine;

import java.util.List;

public abstract class ComicVineResponse<T> {

    int status_code;
    String error;
    List<T> results;

    public int getStatusCode() {
        return status_code;
    }

    public String getError() {
        return error;
    }

    public List<T> getResults() {
        return results;
    }
}
