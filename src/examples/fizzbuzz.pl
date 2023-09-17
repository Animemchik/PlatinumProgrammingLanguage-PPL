def fizzBuzz(n) {
    for (i = 1 | i <= n | i++) {
        if (i % 15 == 0) {
            print("FizzBuzz ")
        } else if (i % 3 == 0) {
            print("Fizz ")
        } else if (i % 5 == 0) {
            print("Buzz ")
        } else {
            print("" + i + " ")
        }
    }
}
fizzBuzz(1500)