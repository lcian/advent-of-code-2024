import Foundation

if let path = Bundle.main.path(forResource: "input", ofType: "txt"){
    let xmas = "XMAS"
    do {
        let data = try String(contentsOfFile: path, encoding: .utf8)
        var lines = data.components(separatedBy: .newlines)
        lines.popLast()
        var res = 0
        for i in 0..<lines.count {
            for jj in 0..<lines[i].count {
                var ok = true
                for kk in 0..<4 {
                    if jj + kk >= lines[i].count {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i].index(lines[i].startIndex, offsetBy: jj + kk)
                    if lines[i][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if jj - kk < 0 {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i].index(lines[i].startIndex, offsetBy: jj - kk)
                    if lines[i][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if i + kk >= lines.count {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i + kk].index(lines[i + kk].startIndex, offsetBy: jj)
                    if lines[i + kk][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if i - kk < 0 {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i - kk].index(lines[i - kk].startIndex, offsetBy: jj)
                    if lines[i - kk][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if i + kk >= lines.count || jj + kk >= lines[i].count {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i + kk].index(lines[i + kk].startIndex, offsetBy: jj + kk)
                    if lines[i + kk][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if i - kk < 0 || jj - kk < 0 {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i - kk].index(lines[i - kk].startIndex, offsetBy: jj - kk)
                    if lines[i - kk][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if i + kk >= lines.count || jj - kk < 0 {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i + kk].index(lines[i + kk].startIndex, offsetBy: jj - kk)
                    if lines[i + kk][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

                ok = true
                for kk in 0..<4 {
                    if i - kk < 0 || jj + kk >= lines[i].count {
                        ok = false
                        break
                    }
                    let k = xmas.index(xmas.startIndex, offsetBy: kk)
                    let j = lines[i - kk].index(lines[i - kk].startIndex, offsetBy: jj + kk)
                    if lines[i - kk][j] != xmas[k] {
                        ok = false
                        break
                    }
                }
                if ok {
                    res += 1
                }

            }
        }
        print(res)
    } catch {
        print(error)
    }
}

if let path = Bundle.main.path(forResource: "input", ofType: "txt"){
    do {
        let data = try String(contentsOfFile: path, encoding: .utf8)
        var lines = data.components(separatedBy: .newlines)
        lines.popLast()
        var res = 0
        for i in 1..<lines.count - 1 {
            for jj in 1..<lines[i].count - 1 {
                var first = false
                var j = lines[i].index(lines[i].startIndex, offsetBy: jj - 1)
                var a = lines[i - 1][j]
                j = lines[i].index(lines[i].startIndex, offsetBy: jj)
                var b = lines[i][j]
                j = lines[i].index(lines[i].startIndex, offsetBy: jj + 1)
                var c = lines[i + 1][j]
                if a == "M" && b == "A" && c == "S" {
                    first = true
                }
                if a == "S" && b == "A" && c == "M" {
                    first = true
                }

                var second = false
                j = lines[i].index(lines[i].startIndex, offsetBy: jj - 1)
                a = lines[i + 1][j]
                j = lines[i].index(lines[i].startIndex, offsetBy: jj)
                b = lines[i][j]
                j = lines[i].index(lines[i].startIndex, offsetBy: jj + 1)
                c = lines[i - 1][j]
                if a == "M" && b == "A" && c == "S" {
                    second = true
                }
                if a == "S" && b == "A" && c == "M" {
                    second = true
                }

                if first && second {
                    res += 1
                }
            }
        }
        print(res)
    } catch {
        print(error)
    }
}
