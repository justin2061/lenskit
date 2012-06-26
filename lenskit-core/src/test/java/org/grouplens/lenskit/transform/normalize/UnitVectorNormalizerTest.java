/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2012 Regents of the University of Minnesota and contributors
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
package org.grouplens.lenskit.transform.normalize;

import it.unimi.dsi.fastutil.longs.LongSortedSet;
import org.grouplens.lenskit.collections.LongSortedArraySet;
import org.grouplens.lenskit.transform.normalize.UnitVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.VectorTransformation;
import org.grouplens.lenskit.vectors.MutableSparseVector;
import org.junit.Test;

import static java.lang.Math.sqrt;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UnitVectorNormalizerTest {
    UnitVectorNormalizer norm = new UnitVectorNormalizer();
    long[] keys = {1, 3, 4, 6};
    LongSortedSet keySet = LongSortedArraySet.wrap(keys);

    @Test
    public void testScale() {
        MutableSparseVector v = new MutableSparseVector(keySet);
        v.set(1, 1);
        v.set(4, 1);
        assertThat(norm.normalize(v.immutable(), v), sameInstance(v));
        assertThat(v.norm(), closeTo(1, 1.0e-6));
        assertThat(v.size(), equalTo(2));
        assertThat(v.get(1), closeTo(1/sqrt(2), 1.0e-6));
        assertThat(v.get(4), closeTo(1/sqrt(2), 1.0e-6));
    }
    
    @Test
    public void testScaleOther() {
        MutableSparseVector v = new MutableSparseVector(keySet);
        v.set(1, 1);
        v.set(4, 1);
        MutableSparseVector ref = new MutableSparseVector(keySet);
        ref.set(1, 1);
        ref.set(6, 1);
        ref.set(3, 2);
        
        VectorTransformation tx = norm.makeTransformation(ref.immutable());
        assertThat(tx.apply(v), sameInstance(v));
        assertThat(v.norm(), closeTo(sqrt(2.0/6), 1.0e-6));
        assertThat(v.size(), equalTo(2));
        assertThat(v.get(1), closeTo(1/sqrt(6), 1.0e-6));
        assertThat(v.get(4), closeTo(1/sqrt(6), 1.0e-6));
        
        assertThat(tx.unapply(v), sameInstance(v));
        assertThat(v.size(), equalTo(2));
        assertThat(v.get(1), closeTo(1, 1.0e-6));
        assertThat(v.get(4), closeTo(1, 1.0e-6));
        assertThat(v.sum(), closeTo(2, 1.0e-6));
        assertThat(v.norm(), closeTo(sqrt(2), 1.0e-6));
    }

}