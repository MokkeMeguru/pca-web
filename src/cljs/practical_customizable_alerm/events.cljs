(ns practical-customizable-alerm.events
  (:require
   [re-frame.core :as re-frame]
   [practical-customizable-alerm.db :as db]
   [practical-customizable-alerm.lstorage :as lstorage]
   [reitit.frontend.controllers :as refc]
   [reitit.frontend.easy :as resy]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 :navigate
 (fn [_cofx [_ & route]]
   {:navigate! route}))


(re-frame/reg-event-fx
 ::get-sounds
 (fn [{:keys [db]} [_ val]]
   {:db (assoc db :show-twirly true)
    :http-xhrio {:method :get
                 :uri (str val "/sound") ;;val + "/sounds"
                 :timeout 8000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::regist-sounds]
                 :on-failure [::bad-http-result]
                 }}))


(re-frame/reg-event-fx
 ::erase-sounds
 (fn [{:keys [db]} [_ val]]
   {:db (assoc db :show-twirly true)
    :http-xhrio {:method :delete
                 :uri (str val "/clear-sound") ;;val + "/sounds"
                 :timeout 8000
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::regist-sounds]
                 :on-failure [::bad-http-result]}}))


(re-frame/reg-event-fx
 ::upload-sounds
 (fn [{:keys [db]} [_  val url name]]
   (println "called!!!" val url name)
   {:db (assoc db :show-twirly true)
    :http-xhrio {:method :post
                 :uri (str val "/sound") ;;val + "/sounds"
                 :timeout 8000
                 :params {:SoundSource url :SoundName name}
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::detect-status]
                 :on-failure [::bad-http-result]}}))

(re-frame/reg-event-db
 ::detect-status
 (fn [db [_ result]]
   ;;(re-frame/dispatch [::get-sounds (lstorage/get-item! "raspihost")])
   (dissoc db :failed-http-result)))

;; (re-frame/dispatch-sync
;;  [::upload-sounds "http://localhost:8080"
;;   "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fbasic-code.wav?alt=media&token=eabf88cf-3428-4642-90f9-293763071bcd"
;;   "basic-c-code.wav"])

(re-frame/reg-event-db
 ::clear-sounds
 (fn [db [_ val]]
   (re-frame/dispatch [::erase-sounds val])
   (assoc db :sounds [])))


(re-frame/reg-event-db
 ::bad-http-result
 (fn [db [_ result]]
   (println result)
   (assoc db :failed-http-result result)))

(re-frame/reg-event-db
 ::regist-sounds
 (fn [db [_  result]]
   (println result)
   (assoc db :sounds (:sounds result))))

;; (re-frame/dispatch [::regist-sounds {:sounds []}])
;; (re-frame/dispatch-sync [::get-sounds "http://localhost:8080"])

(re-frame/reg-fx
 :navigate!bp
 (fn [route]
   (apply resy/push-state route)))


(re-frame/reg-event-db
 :navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (refc/apply-controllers (:controllers old-match) new-match)]
     (when-not (= new-match old-match) (.scrollTo js/window 0 0))
     (assoc db :current-route (assoc new-match :controllers controllers)))))


(re-frame/reg-event-db
 ::toggle-accordion
 (fn [db [_ id]]
   (update-in db [:accordion id] not)))

(re-frame/reg-event-db
 ::reset-accordion
 (fn [db _]
   (assoc db :accordion {})))

(re-frame/reg-event-db
 ::open-shop-modal!
 (fn [db [_ id]]
   (assoc-in db [:shop-modals id] true)))


(re-frame/reg-event-db
 ::close-shop-modal!
 (fn [db [_ id]]
   (assoc-in db [:shop-modals id] false)))



(re-frame/reg-event-db
 ::viewing-shop
 (fn [db [_ shop-id]]
   (println "ship" shop-id)
   (assoc db :viewing-shop shop-id)))



(re-frame/reg-event-db
 ::viewing-setting
 (fn [db [_ setting]]
   (println "setting" setting)
   (assoc db :viewing-setting setting)))

(re-frame/reg-event-db
 ::init-indexed-db
 (fn [db [_ idb]]
   (assoc db :indexed-db idb)))

(re-frame/reg-event-fx
 ::sync-alarms
 (fn [{:keys [db]} [_ val settings]]
   (println "called!!!" val)
   {:db (assoc db :show-twirly true)
    :http-xhrio {:method :post
                 :uri (str val "/alarm") ;;val + "/sounds"
                 :timeout 8000
                 :params {:AlarmList settings}
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::detect-status]
                 :on-failure [::bad-http-result]
                 }}))

(re-frame/reg-event-db
 ::add-alarm
 (fn [db [_ val setting-string]]
   (let [settings (conj (:alarms db) setting-string)]
     (re-frame/dispatch [::sync-alarms val (vec settings)])
     (assoc db :alarms settings))))

(re-frame/reg-event-db
 ::reset-alarm
 (fn [db [_ _]]
   (assoc db :alarms #{})))

(re-frame/reg-event-db
 ::remove-alarm
 (fn [db [_ val setting-string]]
   (let [settings (disj (:alarms db) setting-string)]
     (re-frame/dispatch [::sync-alarms val (vec settings)])
     (assoc db :alarms settings))))
