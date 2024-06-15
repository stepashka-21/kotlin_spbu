class UnsupportedOperation(val op: Char) :
    Exception("Operation '$op' is unsupported") {
}

class NotEnoughNumbers(val available: Int, val line: String) :
    Exception("More than $available numbers required in $line") {
}
class DivisionByZero() :
    Exception("Division by 0 is impossible") {
    }
class TooManyNumbers(val available: Int, val line: String) :
    Exception("Less than $available numbers required in $line") {
}
class UnrealFile(val file: String) :
    Exception("File $file is unreal") {
}
class IncorrectNumbers(val line: String) :
    Exception("Numbers $line are incorrect") {
}
class IncorrectArguments() :
    Exception("Arguments are incorrect")  {
    }