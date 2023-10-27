def fizzBuzz(n) {
    for (i = 1 | i <= n | i++) {
        if (i % 15 == 0) {
            print("FizzBuzz ")
        } elif (i % 3 == 0) {
            print("Fizz ")
        } elif (i % 5 == 0) {
            print("Buzz ")
        } else {
            print("" + i + " ")
        }
    }
}
fizzBuzz(15)
