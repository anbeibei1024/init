//package com.dashen.demeter.common.utils
//
//import io.reactivex.subjects.PublishSubject
//import rx.subjects.SerializedSubject
//import rx.subscriptions.CompositeSubscription
//import java.util.*
//
//
///**
// * 项目名称：demeter
// * 包名：com.dashen.demeter.component
// * 创建人：dashen
// * 创建时间：2017/3/2 10:21
// * 类描述：rxbus消息传递
// * 备注：
// */
//class RxBus// PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
//private constructor() {
//    private val mSubject: SerializedSubject<Any, Any>
//    private val mSubscriptionMap: HashMap<String, CompositeSubscription>? = null
//
//    init {
//        mSubject = SerializedSubject(PublishSubject.create<Any>())
//    }
//
//    /**
//     * 发送事件
//     * @param o
//     */
//    fun post(o: Any) {
//        mSubject.onNext(o)
//    }
//
//    /**
//     * 接收事件
//     * @param type
//     * *
//     * @param <T>
//     * *
//     * @return
//    </T> */
//    fun <T> toObservable(type: Class<T>): Observable<T> {
//        return mSubject.ofType(type)
//    }
//
//    companion object {
//
//        @Volatile private var mInstance: RxBus? = null
//
//        val instance: RxBus
//            get() {
//                if (mInstance == null) {
//                    synchronized(RxBus::class.java) {
//                        if (mInstance == null) {
//                            mInstance = RxBus()
//                        }
//                    }
//                }
//                return mInstance!!
//            }
//    }
//}