import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
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
public class FriendsDbTest {

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        //you other setup here
    }

    @Test
    public void testCreateDatabase(){
        Context context = RuntimeEnvironment.application;
        FriendsOpenHelper openHelper = new FriendsOpenHelper(context);

        SQLiteDatabase db = openHelper.getWritableDatabase();

        Assert.assertNotNull("La base de datos no se creo :(", db);
        Log.d("Test", "Base de datos creada");

        ContentValues friend = new ContentValues();
        friend.put(FriendsDatabaseConstants.COLUMN_FIRST_NAME, "Cruz");
        friend.put(FriendsDatabaseConstants.COLUMN_LAST_NAME, "Castro");
        friend.put(FriendsDatabaseConstants.COLUMN_PHONE, "55-55-55-55");

        Assert.assertEquals("Los datos coinciden", "Cruz",
                friend.getAsString(FriendsDatabaseConstants.COLUMN_FIRST_NAME));
        Log.d("Test", "Los datos coinciden");

        long row = db.insert(FriendsDatabaseConstants.TABLE_FRIENDS, null, friend);

        Assert.assertNotSame("Amigo añadido correctamente", -1, row);
        Log.d("Test", "Amigo añadido correctamente: " + row);

        SQLiteDatabase dbRead = openHelper.getReadableDatabase();

        Cursor cursor = dbRead.query(FriendsDatabaseConstants.TABLE_FRIENDS, null, "first_name = ?", new String[]{"Cruz"}, null, null, null);

        while (cursor.moveToNext()){
            int indexFirstNameColumn = cursor.getColumnIndex(FriendsDatabaseConstants.COLUMN_FIRST_NAME);
            String name = cursor.getString(indexFirstNameColumn);

            Assert.assertEquals("Cruz", name);
            Log.d("Test", name);
        }

        cursor.close();
        openHelper.close();
        context.deleteDatabase(FriendsDatabaseConstants.DATABASE_NAME);

    }
}
