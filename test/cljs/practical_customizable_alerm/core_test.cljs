(ns practical-customizable-alerm.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [practical-customizable-alerm.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
