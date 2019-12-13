/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ferenc Karsany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.obridge.query.util;

public final class StringHelper {

    private StringHelper() {
    }

    public static String toOracleName(String s) {
        StringBuilder result = new StringBuilder();
        String currChar;
        if (s != null && !s.isEmpty()) {
            for (int i = 0; i < s.length(); i++) {
                currChar = s.substring(i, i + 1);
                if (i != 0 && isAllUpperCase(currChar)) {
                    result.append("_")
                          .append(currChar);
                } else {
                    result.append(currChar);
                }
            }
        }
        return result.toString()
                     .toUpperCase()
                     .replaceAll("\\_\\_", "_");
    }

    public static boolean isAllUpperCase(String str) {
        return str != null
                && !str.trim()
                       .isEmpty()
                && str.chars()
                      .allMatch(Character::isUpperCase);
    }

}
