(ns practical-customizable-alerm.views.private
  (:require [re-frame.core :as rf]
            [practical-customizable-alerm.subs :as subs]
            [practical-customizable-alerm.lstorage :as lstorage]
            [practical-customizable-alerm.events :as events]
            [reagent.core :as r]))

(defn alarmLab []
  [:div.column>div.box
   {:style {:background-color "white"}}
   [:div.container
    [:div.columns.is-cerntered
     [:div.column.has-text-centered>h1.title "Alarm Lab"]]]])

(defn soundLab []
  [:div.column>div.box
   {:style {:background-color "white"}}
   [:div.container
    [:div.columns.is-cerntered
     [:div.column.has-text-centered>h1.title "Sound Lab"]]]])

(defn private-nav [viewing-setting]
  [:div.column>div.tabs.is-large.is-centered
   {:style {:border-bottom "solid 0.3rem #dbdbdb"}}
   [:ul
    (if (= @viewing-setting "alarm")
      [:li.is-active>a {:href "#/private/alarm"} "AlarmLab"]
      [:li>a {:href "#/private/alarm"} "AlarmLab"])
    (if (= @viewing-setting "sound")
      [:li.is-active>a {:href "#/private/sound"} "SoundLab"]
      [:li>a {:href "#/private/sound"} "SoundLab"])]])

(defn raspihost []
  (let [raspihost
        (r/atom
         (if-let [raspihost (lstorage/get-item! "raspihost")] raspihost ""))]
    (fn []
      [:div.column
       [:div.container.has-text-centered
        [:h2.my-2 {:style {:font-size "1.5rem"}} "RaspberryPi Settings"]]
       [:div.columns.is-centered>div.column.is-desktop.is-10
        [:p "Your RaspberryPi host addr is ..."]
        [:div.field.is-horizontal.my-2
         [:div.field-label.is-normal>label.label "Addr"]
         [:div.field-body>div.field.is-grouped
          [:p.control.has-icons-left.is-expanded
           [:input.input {:type "textarea" :placeholder "Please input like 192.168.0.101"
                          :value @raspihost
                          :on-change #(do
                                        (reset! raspihost (.. % -target -value)))}]
           [:span.icon.is-small.is-left>i.fab.fa-raspberry-pi]]
          (if (= @raspihost (lstorage/get-item! "raspihost"))
            [:div.control>a.button.is-
             "registered"]
            [:div.control>a.button.is-info
             {:on-click #(do
                           (lstorage/set-item! "raspihost" @raspihost)
                           (.reload js/window.location true))}
             "regist!"])]]]])))


(defn alarm-setting []
  [:div.columns.is-centered
   [:div.column.is-4.my-1.has-text-centered
    [:h2.subtitle "Alarm List"]
    [:div
     [:p "Time"
      [:span
       {:style {:font-size "0.4rem" :color "gray" :margin-left "0.5rem"}}
       "year/month/day"]]]
    [:div.container
     {:style {:overflow-y "scroll" :height "5rem"}}
     [:ul.my-3
      [:li [:p "20:18"]]
      [:li [:p "20:18"]]
      [:li [:p "20:18"]]
      [:li [:p "20:18"]]]]]
   [:div.column.is-6.my-1.has-text-centered
    [:p.subtitle "Select Alarm"]
    [:div.columns.is-centered>div.column.is-9.has-text-left
     [:form
      [:label.label "Time"]
      [:div.columns.is-centered
       [:div.column.is-7>input
        {:style {:width "100%"}
         :type "date"
         :default-value (-> js/window .moment (.format "YYYY-MM-DD"))}]
       [:div.column.is-5>input
        {:type "time" :default-value "06:00"
         :style {:width "100%"}}]]]]
    [:div.columns.is-centered>div.column.is-9.has-text-left
     [:form
      [:label.label "Sound"]
      (if-let [sounds @(rf/subscribe [::subs/sounds])]
        [:div.control.is-expanded
         [:div.select.is-fullwidth
          [:select
           [:option "Select from below sounds"]
           (map
            (fn [sound] ^{:key sound} [:option sound])
            sounds)]]]
        [:p "sound not found. Please go to SoundLab"])]]]])

(defn clear-confirm []
  (if (js/window.confirm "デバイスの全てのデータを消去しても良いですか？")
    (println "OK")
    (println "false")))


(defn sound-setting []
    [:div.columns.is-centered
   [:div.column.is-4.my-1.has-text-centered
    [:h2.subtitle "Sound List"]
    [:div
     [:p "Sound"]]
    [:div.container
     {:style {:overflow-y "scroll" :height "5rem"}}
     [:ul.my-3
      (map
       (fn [sound] ^{:key sound} [:li>p sound])
       @(rf/subscribe [::subs/sounds]))
      ]]
    [:div.container>div.columns
     [:div.column.is-6
      [:button.button.is-info
       {:on-click #(rf/dispatch [::events/get-sounds (lstorage/get-item! "raspihost")])}
       "sync"]]
     [:div.column.is-6
      [:button.button.is-danger
       {:on-click clear-confirm}
       "clear"]]]]
   [:div.column.is-6.my-1.has-text-centered
    [:p.subtitle "Add Sounds"]
    [:div.columns.is-centered>div.column.is-9.has-text-left
     [:form
      [:label.label "Time"]
      [:div.columns.is-centered
       [:div.column.is-7>input
        {:style {:width "100%"}
         :type "date"
         :default-value (-> js/window .moment (.format "YYYY-MM-DD"))}]
       [:div.column.is-5>input
        {:type "time" :default-value "06:00"
         :style {:width "100%"}}]]]]
    [:div.columns.is-centered>div.column.is-9.has-text-left
     [:form
      [:label.label "Sound"]
      (if-let [sounds @(rf/subscribe [::subs/sounds])]
        [:div.control.is-expanded
         [:div.select.is-fullwidth
          [:select
           [:option "Select from below sounds"]
           (map
            (fn [sound] ^{:key sound} [:option sound])
            sounds)]]]
        [:p "sound not found. Please go to SoundLab"])]]]] )

(defn page
  []
  (let [viewing-setting (rf/subscribe [::subs/viewing-setting])]
    [:div.container.page
     {:style {:height "100vh"
              :margin-bottom "10rem"}}
     [:div.columns.mt-5.is-centered>div.column.is-10>div.container.box
      [:h1.title {:style {:text-indent "1rem"}} "Personal Settings"]]
     [:div.columns.my-5.is-centered>div.column.is-10>div.container.box
      [raspihost]]
     [:div.columns.my-5.is-centered>div.column.is-10>div.container.box
      [:div.columns.is-centered [private-nav viewing-setting]]
      (when (= @viewing-setting "alarm")
        (alarm-setting))
      (when (= @viewing-setting "sound")
        (sound-setting))]]))






