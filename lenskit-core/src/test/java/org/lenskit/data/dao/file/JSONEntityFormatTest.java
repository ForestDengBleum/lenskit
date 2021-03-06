/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2016 LensKit Contributors.  See CONTRIBUTORS.md.
 * Work on LensKit has been funded by the National Science Foundation under
 * grants IIS 05-34939, 08-08692, 08-12148, and 10-17697.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.lenskit.data.dao.file;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.lenskit.data.entities.CommonTypes;
import org.lenskit.data.entities.Entities;
import org.lenskit.data.entities.Entity;
import org.lenskit.data.ratings.Rating;
import org.lenskit.data.ratings.RatingBuilder;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class JSONEntityFormatTest {
    @Test
    public void testBareEntity() {
        JSONEntityFormat fmt = new JSONEntityFormat();
        fmt.setEntityType(CommonTypes.USER);

        LineEntityParser lep = fmt.makeParser(Collections.EMPTY_LIST);
        Entity res = lep.parse("{\"$id\": 203810}");
        assertThat(res, notNullValue());
        assertThat(res.getId(), equalTo(203810L));
        assertThat(res.getType(), equalTo(CommonTypes.USER));
        assertThat(res, equalTo(Entities.create(CommonTypes.USER, 203810)));
    }

    @Test
    public void testRating() {
        JSONEntityFormat fmt = new JSONEntityFormat();
        fmt.setEntityType(CommonTypes.RATING);
        fmt.setEntityBuilder(RatingBuilder.class);

        LineEntityParser lep = fmt.makeParser(Collections.EMPTY_LIST);
        Entity res = lep.parse("{\"$id\": 203810, \"user\": 42, \"item\": 20, \"rating\": 3.5}");
        assertThat(res, notNullValue());
        assertThat(res, instanceOf(Rating.class));
        Rating r = (Rating) res;
        assertThat(r.getId(), equalTo(203810L));
        assertThat(r.getType(), equalTo(CommonTypes.RATING));
        assertThat(r.getUserId(), equalTo(42L));
        assertThat(r.getItemId(), equalTo(20L));
        assertThat(r.getValue(), equalTo(3.5));
    }
}