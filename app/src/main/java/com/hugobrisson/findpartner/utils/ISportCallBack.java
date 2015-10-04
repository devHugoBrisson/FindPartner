package com.hugobrisson.findpartner.utils;

import com.hugobrisson.findpartner.model.Sport;
import com.hugobrisson.findpartner.model.SportUser;

import java.util.List;

/**
 * Created by hugo on 14/06/2015.
 */
public interface ISportCallBack {
   void getSports(List<Sport> sports);
}
