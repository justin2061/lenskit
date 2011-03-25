/*
 * LensKit, a reference implementation of recommender algorithms.
 * Copyright 2010-2011 Regents of the University of Minnesota
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
package org.grouplens.lenskit.eval.crossfold;

import java.util.ArrayList;
import java.util.Collections;

import org.grouplens.lenskit.data.Rating;
import org.grouplens.lenskit.data.Ratings;

/**
 * Split user rating profiles by holding out the most recent ratings.
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 */
public class TimestampUserRatingProfileSplitter extends OrderedFractionalUserRatingProfileSplitter {
    public TimestampUserRatingProfileSplitter(double fraction) {
		super(fraction);
	}

	@Override
    protected void orderRatings(ArrayList<Rating> ratings) {
    	Collections.sort(ratings, Ratings.TIMESTAMP_COMPARATOR);
    }
}