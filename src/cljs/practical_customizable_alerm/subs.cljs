(ns practical-customizable-alerm.subs
  (:require
   [re-frame.core :as re-frame]
   [practical-customizable-alerm.idb :as idb]))

(re-frame/reg-sub
 :current-route
 (fn [db]
   (:current-route db)))


(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))


(re-frame/reg-sub
 ::accordion-toggle
 (fn [db]
   (:accordion db)))

(re-frame/reg-sub
 ::shop-modal
 (fn [db [_ idx]]
   (get (:shop-modals db) idx)))

(re-frame/reg-sub
 ::shop-modals
 (fn [db]
   (vals (:shop-modals db))))

(re-frame/reg-sub
 ::viewing-shop
 (fn [db]
   (:viewing-shop db)))

(re-frame/reg-sub
 ::viewing-setting
 (fn [db]
   (:viewing-setting db)))

(re-frame/reg-sub
 ::indexed-db
 (fn [db]
   (:indexed-db db)))

;; 
(re-frame/reg-sub
 ::raspihost
 (fn [db]
   (:raspihost db)))

(re-frame/reg-sub
 ::sounds
 (fn [db]
   (:sounds db)))

