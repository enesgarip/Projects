{-# OPTIONS_GHC -Wno-incomplete-patterns #-}
{- 
    This program aims to create a family tree based on fully functioned Haskell Language. Code is incomplete.
    Authors:
    Ahmet Tunahan Cinsoy
    Abbas Göktuğ Yılmaz
    Veysi Öz
    Enes Garip

-}
import Text.Printf

data Person = Person { gender :: String
                     , name :: String
                     , surname :: String
                     , birthDate :: String
                     , deathDate :: String
                     , father :: String
                     , mother :: String
                     , spouse :: String
                     } deriving Show


tree :: [Person]
tree = [Person "M" "Ali" "Yilmaz" "1/1/1927" "1/1/1997" "null" "null" "null",
        Person "F" "Ayse" "Yilmaz" "1/1/1930" "1/1/2000" "null" "null" "Ali",
        Person "F" "Burcu" "Cetin" "1/1/1955" "null" "Ali" "Ayse" "Fatih",
        Person "M" "Fatih" "Cetin" "1/1/1952" "1/1/2020" "null" "null" "Burcu",
        Person "M" "Tolga" "Yilmaz" "1/1/1960" "null" "Ali" "Ayse" "null"]


printRelation :: Person -> Person -> IO ()
printRelation person person2
  | stringComparison (name person) (father person2) = printf "\nBaba:%s Cocuk:%s\n" (name person) (name person2)
  | stringComparison (name person) (mother person2) = printf "\nAnne:%s Cocuk:%s\n" (name person) (name person2)
  | stringComparison (name person) (spouse person2) = printf "\n%s ile %s es\n" (name person) (name person2)
  | stringComparison (name person2) (mother person) = printf "\nAnne:%s Cocuk%s\n" (name person2) (name person)
  | stringComparison (name person2) (father person) = printf "\nBaba:%s Cocuk:%s\n" (name person2) (name person)
  | stringComparison (name person2) (spouse person) = printf "\n%s ile %s es\n" (name person2) (name person)
  | stringComparison (father person) (father person2) = printf "\n%s ile %s kardes\n" (name person) (name person2)
  | stringComparison (mother person) (mother person2) = printf "\n%s ile %s kardes\n" (name person) (name person2)
  | otherwise = putStrLn "Error!"

stringComparison :: String -> String -> Bool
stringComparison a b = a == b

findFromList :: String -> Int -> [Int]
findFromList x y = if stringComparison x (name (tree!!y)) then return y else findFromList x (y-1)


relationOfTwoPeople  = do
    putStrLn "Enter name of person 1:"
    name1 <- getLine
    putStrLn "Enter name of person 2:"
    name2 <- getLine
    let firstIndex = findFromList name1 (length tree-1)
    let secondIndex = findFromList name2 (length tree-1)
    printRelation (tree!!head firstIndex) (tree!!head secondIndex)
    main



addPerson = do
    putStrLn "Gender(M/F): "
    gender <- getLine
    putStrLn "Name: "
    name <- getLine
    putStrLn "Surname: "
    surname <- getLine
    putStrLn "Birth Date: "
    birth_date <- getLine
    putStrLn "Death Date: "
    death_date <- getLine
    putStrLn "Father Name: "
    father <- getLine
    putStrLn "Mother Name: "
    mother <- getLine
    putStrLn "Spouse: "
    spouse <- getLine
    let t = [Person gender name surname birth_date death_date father mother spouse]
    let q = t ++ tree
    print tree
    print q
    main

printTree :: IO ()
printTree = do
    print tree
    main


main = do
    putStrLn "\n1) Add a person to tree.\n2) Update a person in tree.\n3) Print the tree.\n4) Ask relationship between 2 people.\n5) Exit"
    putStrLn "Choose an option: "
    o <- readLn
    case o of
        1 -> putChar '\n' >> addPerson >> main
        2 -> putChar '\n' >> putStrLn "Hello World2" >> main
        3 -> putChar '\n' >> printTree
        4 -> putChar '\n' >> relationOfTwoPeople >> main
        5 -> return()