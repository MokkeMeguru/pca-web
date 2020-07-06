(ns practical-customizable-alerm.alarm-serialize
  (:require [re-frame.core :as rf]
            [practical-customizable-alerm.events :as events]
            [practical-customizable-alerm.lstorage :as lstorage]))


;; (re-frame/dispatch [::reset-alarm])
;; (re-frame/dispatch [::add-alarm "1,2020-06-19-08-30,1,/home/pi/PPS/assets/PretenderWithPiano.ogg,3"])
(defn format-setting [s]
  (let [bases (clojure.string/split s ",")
        date (clojure.string/split (nth bases 1) "-")
        format-date (clojure.string/join "/" (take 3 date))
        format-time (clojure.string/join ":" (drop 3 date))
        format-name (last (clojure.string/split (nth bases 3) "/"))
        format-freq (last bases)]
    ;;bases
    [format-date format-time format-name format-freq]
    ))

(defn serial-setting [[date time name freq]]
  (let [serial-name (str "/home/pi/PPS/assets/" name)
        serial-date
        (clojure.string/join
         "-"
         (-> date (clojure.string/split "/")))
        serial-time (clojure.string/join "-" (-> time (clojure.string/split ":")))
        serial-datetime (str serial-date  "-" serial-time)
        serial-id (str 1)
        serial-source (str 2)
        serial-repeat (str freq)]
    (clojure.string/join
     ","
     [serial-id serial-datetime serial-source serial-name serial-repeat])))


(defn re-format-setting-as-hiccup [s]
  (let [[d t n f] (format-setting s)]
    [:div.columns
     [:div.column.is-10>p t [:span {:style {:font-size "0.4rem" :color "gray" :margin "0 0.5rem"}} d]
      [:span " (" n " "] f ")" ]
     [:div.column.is-2
      [:button.button.is-small {:on-click #(rf/dispatch [::events/remove-alarm (lstorage/get-item! "raspihost") s])
                                :style {:margin-left "auto"}} "X"]]]))

(defn re-format-date [date]
  (clojure.string/replace date #"-" "/"))

;; (re-format-date "2020-10-02")


;; (re-format-setting-as-hiccup (format-setting  "1,2020-06-19-08-30,1,/home/pi/PPS/assets/PretenderWithPiano.ogg,3"))


;; (format-setting (serial-setting (format-setting  "1,2020-06-19-08-30,1,/home/pi/PPS/assets/PretenderWithPiano.ogg,3")))





;; (map str (sort #{1 2 3}))
