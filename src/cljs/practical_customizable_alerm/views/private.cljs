(ns practical-customizable-alerm.views.private
  (:require [re-frame.core :as rf]
            [practical-customizable-alerm.subs :as subs]
            [practical-customizable-alerm.lstorage :as lstorage]
            [practical-customizable-alerm.events :as events]
            [reagent.core :as r]
            [practical-customizable-alerm.alarm-serialize :as aser]))

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
                                        (reset! raspihost (.. % -target -value)))
                          }]
           [:span.icon.is-small.is-left>i.fab.fa-raspberry-pi]]
          (if (= @raspihost (lstorage/get-item! "raspihost"))
            [:div.control>a.button.is-
             "registered"]
            [:div.control>a.button.is-info
             {:on-click #(do
                           (lstorage/set-item! "raspihost" @raspihost)
                           (.reload js/window.location true))}
             "regist!"])]]]])))


(defn add-alarm-setting []
  (let [date (r/atom (-> js/window .moment (.format "YYYY-MM-DD")))
        time (r/atom "06:00")
        sound (r/atom "")
        repeat-time (r/atom 0)]
    (fn []
      [:form {:action "#/private/alarm"}
       [:div.columns.is-centered>div.column.is-9.has-text-left
        [:label.label "Time"]
        [:div.columns.is-centered
         [:div.column.is-7>input
        {:style {:width "100%"}
         :type "date"
         :on-change #(reset! date (.. % -target -value))
         :default-value (-> js/window .moment (.format "YYYY-MM-DD"))}]
         [:div.column.is-5>input
          {:type "time" :default-value "06:00"
           :on-change #(reset! time (.. % -target -value))
           :style {:width "100%"}}]]]
       [:div.columns.is-centered>div.column.is-9.has-text-left
        [:label.label "Sound"]
        (let [sounds (rf/subscribe [::subs/sounds])]
          (if (pos-int? (count @sounds))
          [:div.control.is-expanded
           [:div.select.is-fullwidth 
            [:select {:required true
                      :on-change #(reset! sound (.. % -target -value))}
             [:option {:value ""} "Select from below sounds"]
             (map
              (fn [sound] ^{:key sound} [:option {:value sound} sound])
              @sounds)]]]
          [:div.my-2[:p "sound not found. Please go to " [:a {:href "#/private/sound"}"SoundLab"]]]))]
       [:div.columns.is-centered>div.column.is-9.has-text-left
        [:label.label "Repeat time"]
        [:div.columns.is-centered
         [:div.column.is-7>input
          {:style {:width "100%"}
           :on-change #(reset! repeat-time (.. % -target -value))
           :type "number"
           :default-value 0}]
         [:p.py-3 "※ 0 で無制限繰り返し"]]]
       (if (not= "" @sound)
        [:p.control>button.button.is-info
         {:on-click
          #(do (println "Hello Send")
               (println (aser/re-format-date @date) @time @sound @repeat-time)
               (println (aser/serial-setting [(aser/re-format-date @date) @time @sound @repeat-time]))
               (rf/dispatch
                [::events/add-alarm
                 (lstorage/get-item! "raspihost")
                 (aser/serial-setting [(aser/re-format-date @date) @time @sound @repeat-time])])
               (.preventDefault %))}
         "Add + Sync"]
        [:p.control>button.button
         {:on-click
          #(.preventDefault %)}
         "Imperfect"]
        )]
      )))

(defn alarm-setting []
  [:div.columns.is-centered
   [:div.column.is-5.my-1.has-text-centered
    [:h2.subtitle "Alarm List"]
    [:div
     [:p "Time"
      [:span
       {:style {:font-size "0.4rem" :color "gray" :margin "0 0.5rem"}}
       "year/month/day"]
      [:span "(sound source repeat)"]]]
    [:div.container
     {:style {:overflow-y "scroll" :height "10rem"}}
     [:ul.my-3 {:style {:text-align "left"}}
      (map (fn [m] 
             [:li {:key m}
              (-> m
                  aser/re-format-setting-as-hiccup)])
           @(rf/subscribe [::subs/alarms]))]]]
   [:div.column.is-6.my-1.has-text-centered
    [:p.subtitle "Select Alarm"]
    [add-alarm-setting]
    ]])


(defn clear-confirm []
  (if (js/window.confirm "デバイスの全てのデータを消去しても良いですか？")
    (do
      (println "OK")
      (rf/dispatch [::events/clear-sounds (lstorage/get-item! "raspihost")]))
    (println "false")))

(defn add-sounds []
  (let [url (r/atom "")
        filename (r/atom "")]
   (fn []
     [:div.column.is-6.my-1.has-text-centered
    [:p.subtitle "Add Sounds"]
    [:div.container.is-centered {:style {:margin "auto"}}
     [:div.columns.is-centered>div.field>form
      [:div.field.is-horizontal.my-5
       [:div.field-label.is-normal>label.label "URL"]
       [:div.field-body.is-normal>div.field>input.input
        {:type "text" :placeholder "https://firebasestorage.googleapis.com/..."
         :value @url
         :on-change #(reset! url (.. % -target -value))
         }]]
      [:div.field.is-horizontal.my-5
       [:div.field-label.is-normal>label.label "File Name"]
       [:div.field-body.is-normal>div.field>input.input
        {:type "text" :placeholder "sound.wav"
         :value @filename
         :on-change #(reset! filename (.. % -target -value))}]]]
     [:div.columns.is-centered>div.field.is-grounded.is-grouped.is-grouped-centered
      [:p.control
       (if (and (not= "" @url) (not= "" @filename))
           ;; 
         [:button.button.is-info
          {:on-click
           #(when (and (not= "" @url) (not= "" @filename))
              (rf/dispatch-sync
               [::events/upload-sounds (lstorage/get-item! "raspihost")
                @url
                @filename
                ;; "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fbasic-code.wav?alt=media&token=eabf88cf-3428-4642-90f9-293763071bcd"
                ;; "basic-c-code.wav"
                ])
              (rf/dispatch [::events/get-sounds (lstorage/get-item! "raspihost")]))
           }
          "regist!"]
         [:button.button.is-info
          "we cannot download the file"])
       ]]]])))

(defn sound-setting []
  [:div.columns.is-centered
   [:div.column.is-4.my-1.has-text-centered
    [:h2.subtitle "Sound List"]
    [:div
     [:p "Sound"]]
    [:div.container.my-3
     {:style {:overflow-y "scroll" :height "5rem"}}
     [:ul.my-3
      (map
       (fn [sound] ^{:key sound} [:li>p sound])
       @(rf/subscribe [::subs/sounds]))
      ]]
    [:div.container>div.columns
     [:div.column.is-6
      [:button.button.is-info
       {:on-click #(rf/dispatch
                    [::events/get-sounds (lstorage/get-item! "raspihost")])}
       "sync"]]
     [:div.column.is-6
      [:button.button.is-danger
       {:on-click clear-confirm}
       "clear"]]]]
   [add-sounds]])


(defn page
  []
  (let [viewing-setting (rf/subscribe [::subs/viewing-setting])]
    [:div.container.page
     {:style {:margin-bottom "10rem"}}
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
