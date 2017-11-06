package com.one.hotchpotch.activity;

import android.widget.Button;

import com.one.hotchpotch.BuildConfig;
import com.one.hotchpotch.R;
import com.one.login.ui.RegisterActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * Created by ${User} on 2017/7/3.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RegistTest {
    private RegisterActivity registerActivity;
    private Button btn_login;
    private Button dialogBtn;
    private Button toastBtn;

    @Before
    public void setUp() {
        registerActivity = Robolectric.setupActivity(RegisterActivity.class);
        btn_login = (Button) registerActivity.findViewById(R.id.btn_login);
    }
    @Test
    public void gotoLoginActivity(){
        RegisterActivity registerActivity = Robolectric.setupActivity(RegisterActivity.class);
        assertNotNull(registerActivity);
        assertEquals(registerActivity.getTitle(),"RegisterActivity1");
    }
}
