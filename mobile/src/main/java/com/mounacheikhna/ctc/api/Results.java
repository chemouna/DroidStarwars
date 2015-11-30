package com.mounacheikhna.ctc.api;

import retrofit.Result;
import rx.functions.Func1;

public final class Results {
  private static final Func1<Result<?>, Boolean> IS_SUCCESS = new Func1<Result<?>, Boolean>() {
    @Override public Boolean call(Result<?> result) {
      return !result.isError() && result.response().isSuccess();
    }
  };

  public static Func1<Result<?>, Boolean> isSuccess() {
    return IS_SUCCESS;
  }

  private static final Func1<Result<?>, Boolean> IS_ERROR =
      new Func1<Result<?>, Boolean>() {
        @Override public Boolean call(Result<?> result) {
          return result.isError() || !result.response().isSuccess();
        }
      };

  public static Func1<Result<?>, Boolean> isError() {
    return IS_ERROR;
  }

  private Results() {}
}
