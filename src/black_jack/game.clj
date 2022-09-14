(ns black-jack.game
  (:require [card-ascii-art.core :as card]))

;; Cartas: A, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K
(defn new-card []
  "Generate the cards from A to K for Black-Jack"
  (inc (rand-int 13)))

;; Jogador
(defn player
  [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)]
    {:player-name player-name
     :cards [card-1 card-2]}))
(card/print-player (player "Edson Garcia"))
(card/print-player (player "Dealer"))

;; dentro da pasta D:\black jack Clojure\black-jack\src\black_jack> via Powershel
;; rodar o comando: lein run -m black-jack.game
;; flag -m indica o namespace, black-jack.game é o namespace que contém a aplicação

;; (ns black-jack.game)

;; ;; Cartas: A, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K
;; (defn new-card []
;;   "Generate the cards from A to K for Black-Jack"
;;   (inc (rand-int 13)))

;; ;; Jogador
;; (defn player
;;   [player-name]
;;   (let [card-1 (new-card)
;;         card-2 (new-card)]
;;     {:player-name player-name
;;      :cards [card-1 card-2]}))

;; (println (player "Edson Garcia"))
;; (println (player "Dealer"))
