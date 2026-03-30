import sys
from collections import Counter

input_dir = Path(sys.argv[1])
num_merges = int(sys.argv[2])
output_file = Path(sys.argv[3])

lines = []
with open('test.txt') as file:
    for line in file:
        lines.append([c for c in line.strip()])

pairs = []

for line in lines:
    for i in range(len(line) - 1):
        pairs.append((line[i], line[i + 1]))


counter = Counter(pairs)
most_common = counter.most_common(1)
most_common_pair = most_common[0][0]
pair = most_common_pair[0] + most_common_pair[1]

for line in lines:
    for i in range(len(line) - 2):
        if line[i] == most_common_pair[0] and line[i + 1] == most_common_pair[1]:
            line[i] = pair
            del line[i + 1]

print(lines)