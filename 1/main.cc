// Copyright 2024 <Lorenzo Cian>
#include <algorithm>
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>

void solve_1() {
  std::ifstream file("input.txt");
  std::string line;
  std::vector<int> left = {};
  std::vector<int> right = {};
  while (std::getline(file, line)) {
    size_t i = line.find_first_of(' ');
    left.push_back(atoi(line.substr(0, i).c_str()));
    right.push_back(atoi(line.substr(i + 1).c_str()));
  }
  std::sort(left.begin(), left.end());
  std::sort(right.begin(), right.end());
  int res = 0;
  for (size_t i = 0; i < left.size(); ++i) {
    res += abs(left.at(i) - right.at(i));
  }
  std::cout << res << std::endl;
}

void solve_2() {
  std::ifstream file("input.txt");
  std::string line;
  std::vector<int> left{};
  std::unordered_map<int, int> right{};
  while (std::getline(file, line)) {
    size_t i = line.find_first_of(' ');
    left.push_back(atoi(line.substr(0, i).c_str()));
    right[atoi(line.substr(i + 1).c_str())]++;
  }
  long res = 0;
  for (size_t i = 0; i < left.size(); ++i) {
    int x = left.at(i);
    res += x * right[x];
  }
  std::cout << res << std::endl;
}

int main() {
  solve_1();
  solve_2();
  return 0;
}
