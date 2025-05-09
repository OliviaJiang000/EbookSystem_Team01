import csv
import requests
import time

# Google Books API 查询函数
def get_extra_info(isbn):
    url = f'https://www.googleapis.com/books/v1/volumes?q=isbn:{isbn}'
    try:
        response = requests.get(url)
        if response.status_code == 200:
            data = response.json()
            if 'items' in data and len(data['items']) > 0:
                info = data['items'][0]['volumeInfo']
                genre = ', '.join(info.get('categories', []))
                description = info.get('description', '')
                return genre, description
    except Exception as e:
        print(f"Error fetching data for ISBN {isbn}: {e}")
    return '', ''

# 输入 CSV 和输出 CSV 文件路径
input_file = 'books_with_images_Google.csv'
output_file = 'books_with_images_genres_Google.csv'

# 初始化输出列表
output_data = []

with open(input_file, newline='', encoding='utf-8') as csvfile:
    reader = csv.DictReader(csvfile)
    for i, row in enumerate(reader):
        if i >= 199:
            break

        image_url = row['image_url'].strip()
        if not image_url:
            continue  # 跳过没有封面的书

        title = row['title']
        author = row['authors']
        isbn13 = row['isbn13']
        available_copies = 10
        loan_duration = 14

        genre, description = get_extra_info(isbn13)

        output_data.append({
            'title': title,
            'author': author,
            'genre': genre,
            'description': description,
            'cover_image': image_url,
            'available_copies': available_copies,
            'loan_duration': loan_duration
        })

        print(f"✅ Processed: {title}")
        time.sleep(0.1)  # 防止请求过快

# 写入新的 CSV 文件
fieldnames = ['title', 'author', 'genre', 'description', 'cover_image', 'available_copies', 'loan_duration']
with open(output_file, 'w', newline='', encoding='utf-8') as csvout:
    writer = csv.DictWriter(csvout, fieldnames=fieldnames)
    writer.writeheader()
    writer.writerows(output_data)

print(f"\n✅ 已保存到 {output_file}，共 {len(output_data)} 本书。")
