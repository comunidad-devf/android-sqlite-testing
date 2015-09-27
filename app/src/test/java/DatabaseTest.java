import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
import android.util.Log;

import com.devf.sqlite_sample.BuildConfig;
import com.devf.sqlite_sample.data.FriendsDatabaseConstants;
import com.devf.sqlite_sample.data.FriendsOpenHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;


/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Created by Pedro Hernández on 09/2015.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class DatabaseTest{

    Context context;
    FriendsOpenHelper friendsDb;

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        //you other setup here
    }

    @Test
    public void testAddEntry(){
        context = RuntimeEnvironment.application;
        context.deleteDatabase(FriendsDatabaseConstants.DATABASE_NAME);

        friendsDb = new FriendsOpenHelper(context);
        SQLiteDatabase db = friendsDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FriendsDatabaseConstants.COLUMN_FIRST_NAME, "Edy");
        values.put(FriendsDatabaseConstants.COLUMN_LAST_NAME, "Williams");
        values.put(FriendsDatabaseConstants.COLUMN_PHONE, "55-55-55-55");

        Assert.assertNotNull(values);


        long row = db
                .insert(FriendsDatabaseConstants.TABLE_FRIENDS, null, values);

        Assert.assertTrue(row != -1);

        friendsDb.close();
    }

}
