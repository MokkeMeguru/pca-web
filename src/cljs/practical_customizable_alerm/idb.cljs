(ns practical-customizable-alerm.idb
  (:require [re-frame.core :as re-frame]
            [practical-customizable-alerm.events :as events]
            [reagent.core :as r]))

(def idxm [{:name "sounds" :key-path "id"}
           {:name "raspihome" :key-path "id"}
           {:name "alarm" :key-path "id"}])

;; event

;; (re-frame/reg-event-db
;;  ::init-indexed-db
;;  (fn [db [_ indexed-db]]
;;    (println "db" indexed-db)
;;    (assoc db :indexed-db indexed-db)))


;; subs
;; (re-frame/reg-sub
;;  ::indexed-db
;;  (fn [db]
;;    (:indexed-db db)))


;; functions

(defn check-idb []
  (if (. js/window -indexedDB)
    true
    false))

;; (check-idb)

(defn idb-request-error [e]
  (.error js/console "it raised error when i request IndexedDB" e))

(defn idb-request-success [e]
  (.log js/console "successed request")
  (re-frame/dispatch [::events/init-indexed-db (.. e -target -result)]))

(def sound-table
  {:name "sounds"
   :index
   [{:name "name" :unique true}]})

(def alarm-table
  {:name "alarm"
   :index
   [{:name "time" :unique true}
    {:name "sound" :unique false}]})


(defn create-object-store [db table-info]
  (let [object-store (.createObjectStore db (:name table-info) #js {:keyPath "id" :autoIncrement true})]
    (for [table-idx (:index table-info)]
      (.createIndex
       object-store
       (:name table-idx) (:name table-idx) #js {:unique (:unique table-idx)}))
    ;; (set! (.. object-store -transaction -oncomplete)
    ;;       (fn [e]
    ;;         (let [sound-object-store (-> db
    ;;                                      (.transaction "sounds" "readwrite")
    ;;                                      (.objectStore "sounds"))]
    ;;           (.add sound-object-store #js {:name "sample"})
    ;;           (.add sound-object-store #js {:name "sample2"}))))
    ))

(defn add-obect-store [db new-sounds]
  (let [transaction (.transaction db #js ["sounds"] "readwrite")]
    (set! (.-oncomplete transaction) (fn [e] (.log js/console "all done !" e)))
    (set! (.-onerror transaction) idb-request-error)
    (let [object-store (.objectStore transaction "sounds")]
      (for [sound new-sounds]
        (do
          (.log js/console sound)
         (->
          object-store
          (.put (clj->js sound))))))))

(defn get-all-object-store [db]
  (let [values (r/atom [])
        request
        (->
         (.transaction db #js ["sounds"] "readonly")
         (.objectStore "sounds")
         .openCursor)]
    (set! (.-onerror request) idb-request-error)
    (set! (.-onsuccess request)
          (fn [e]
            (if-let [cursor (.. e -target -result)]
              (do
                (.log js/console (.-value cursor))
                (swap! values conj (.-value cursor))
                (.continue cursor))
              (do
                (.log js/console "values" @values)
                ))))))

(defn delete-all-object-store [db]
  (let [transaction (.transaction db #js ["sounds"] "readwrite")]
    (set! (.-oncomplete transaction) (fn [e] (.log js/console "erase complete")))
    (set! (.-onerror transaction) idb-request-error)
    (let [request (-> (.objectStore transaction "sounds")
                      .clear)]
      (set! 
       (.-onsuccess request)
       (fn [e] (.log js/console "finished"))))))

(defn get-object-store [db idx]
  (let [request (->
                 (.transaction db #js ["sounds"] "readonly")
                 (.objectStore "sounds")
                 (.get idx))]
    (set! (.-oncomplete request) (fn [e] (.log js/console "complete")))
    (set! (.-onerror request) idb-request-error)
    (set! (.-onsuccess request) (fn [e] (.log js/console "find! " (.. e -target -result -name))))
    ))

(def dbname "pca-private")


;; ;; initialize
;; (let [request (.open
;;                (. js/window -indexedDB)
;;                dbname 4)]
;;   (set! (.-onerror request) idb-request-error)
;;   (set! (.-onupgradeneeded request)
;;         (fn [e]
;;           (let [db (.. e -target -result)]
;;             (re-frame/dispatch-sync [::init-indexed-db db])
;;             (create-object-store db sound-table)))))


;; ;; add

;; (def new-sounds [{:name "sample3"} {:name "sample4"}])
;; (add-obect-store @(re-frame/subscribe [::indexed-db]) new-sounds)

;; (get-object-store @(re-frame/subscribe [::indexed-db]) 9)

;; ;; get all
;; (get-all-object-store @(re-frame/subscribe [::indexed-db]))

;; ;; delete all
;; (delete-all-object-store @(re-frame/subscribe [::indexed-db]))



;; (let [test "test"]
;;   (js/console.log "add")
;;   (set! (.-onerror request) idb-request-error)
;;   (set! (.-onupgradeneeded request)
;;         (fn [e]
;;           (add-object-store db new-sounds))))




;; (defn createObjectStore [db]
;;   (let [object-store (.createObjectStore db "sounds" #js {:keyPath "id" :autoIncrement true})
;;         _  (.createIndex objectStore "name" "name" #js {:unique true})]
;;     (set!
;;      (.. object-store -transaction -oncomplete)
;;      #(let [sound-object-store (-> db
;;                                  (.transaction #js ["sounds"] "readwrite")
;;                                  (.objectStore "sounds"))]
;;         (map (fn [m]
;;                (js/console.log m)
;;                (.add sound-object-store  (clj->js m))
;;                )
;;              sound-data)))))

;; (defn open-idb
;;   ([name version]
;;    (let [request (->
;;                   (. js/window -indexedDB) 
;;                   (.open name version))]
;;      (set! (.-onupgradeneeded request)
;;            #(do
;;               (println "test")
;;               (createObjectStore (.. % -target -result))
;;               (re-frame/dispatch [::init-indexed-db (.. % -target -result)])
;;               ))
;;      (set! (.-onsuccess request) #(re-frame/dispatch [::init-indexed-db (.. % -target -result)]))
;;      (set! (.-onerror request) (fn [e]
;;                                  (.alart js/console e))
;;            ;;idb-request-error
;;            ;; #(.error js/console (.. % -target -result))
;;            )
     
;;      ))
;;  )




;; (.log js/console (open-idb "pca-idb" 2))


;; (let [request (.open (.-indexedDB js/window) "testdb")]
;;   (set! (.-onerror request) (fn [e] (.log js/console  "what rise?")))
;;   (set! (.-onsuccess request) (fn [e] (.log js/console "success test!"))))

