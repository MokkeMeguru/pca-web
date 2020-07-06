(ns practical-customizable-alerm.lstorage)

(defn set-item!
  [k v]
  (.setItem (.. js/window -localStorage) k v))

(defn get-item!
  [k]
  (.getItem (.. js/window -localStorage) k))

(defn remove-item!
  [k]
  (.removeItem (.. js/window -localStorage) k))
